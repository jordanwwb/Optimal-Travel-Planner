/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Booking;
import entity.Review;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.BookingNotMatchException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import ws.DataModel.ReviewHandler;

/**
 * REST Web Service
 *
 * @author sucram
 */

//ReviewSessionBean: createNewReview, deleteReview, retrieveReviewByServiceId, updateReview
@Path("Review")
public class ReviewResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ReviewResource
     */
    public ReviewResource() {
    }
    
    ReviewSessionBeanLocal reviewSessionBeanLocal = lookupReviewSessionBeanLocal();

    BookingSessionBeanLocal bookingSessionBeanLocal = lookupBookingSessionBeanLocal();
    
    @Path("Create")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReview(ReviewHandler objHandler) {
        if (objHandler != null) {
            try {
                Booking booking = (Booking) objHandler.getBooking();
                Long reviewId
                        = reviewSessionBeanLocal.createNewReview(
                                booking.getBookingId(),
                                objHandler.getReview()).getReviewId();

                return Response.status(Response.Status.OK).entity(reviewId).build();

            
            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update request").build();
        }
    }

    @Path("Update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReview(ReviewHandler objHandler) {
        if (objHandler != null) {
            try {
                Booking booking = (Booking) objHandler.getBooking();
                Review review = objHandler.getReview();
                if (!review.getBooking().getBookingId().equals(booking.getBookingId())) {
                    throw new BookingNotMatchException("Please ensure review matches booking!");
                }
                reviewSessionBeanLocal.updateReview(review);
                review.cleanRelationships();

                return Response.status(Response.Status.OK).entity(review).build();

            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update request").build();
        }
    }

   


    @Path("Delete/{reviewId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReview(ReviewHandler objHandler,
            @PathParam("reviewId") Long reviewId) {
        try {
             Booking booking = (Booking) objHandler.getBooking();
            if (!booking.getBookingId().equals(reviewSessionBeanLocal.retrieveReviewByReviewId(reviewId).getBooking().getBookingId())) {
                throw new BookingNotMatchException("Please ensure travel itinerary matches booking!");
            }
            reviewSessionBeanLocal.deleteReview(reviewId);
            return Response.status(Status.OK).build();
        } catch (Exception ex) {
            return Response.status(Status.METHOD_NOT_ALLOWED).entity(ex.getMessage()).build();
        }
    }

    private BookingSessionBeanLocal lookupBookingSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (BookingSessionBeanLocal) c.lookup("java:global/OptimalTravelPlan/OptimalTravelPlan-ejb/BookingSessionBean!ejb.session.stateless.BookingSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ReviewSessionBeanLocal lookupReviewSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReviewSessionBeanLocal) c.lookup("java:global/OptimalTravelPlan/OptimalTravelPlan-ejb/ReviewSessionBean!ejb.session.stateless.ReviewSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
