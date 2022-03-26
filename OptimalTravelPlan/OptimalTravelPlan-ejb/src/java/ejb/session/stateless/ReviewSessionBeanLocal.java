/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Review;
import java.util.List;
import javax.ejb.Local;
import util.exception.BookingNotFoundException;
import util.exception.ConstraintViolationException;
import util.exception.ReviewNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author sucram
 */
@Local
public interface ReviewSessionBeanLocal {

    /**
     *
     * @param bookingId
     * @param review
     * @return
     * @throws BookingNotFoundException
     * @throws UnknownPersistenceException
     * @throws ConstraintViolationException
     */
    public Review createNewReview(Long bookingId, Review review) throws BookingNotFoundException, UnknownPersistenceException, ConstraintViolationException;

    /**
     *
     * @param serviceId
     * @return
     */
    public List<Review> retrieveReviewsByServiceId(Long serviceId);

    /**
     *
     * @param review
     * @throws ReviewNotFoundException
     */
    public void updateReview(Review review) throws ReviewNotFoundException;

    /**
     *
     * @param reviewId
     * @return
     * @throws ReviewNotFoundException
     */
    public Review retrieveReviewByReviewId(Long reviewId) throws ReviewNotFoundException;

    /**
     *
     * @param reviewId
     * @throws ReviewNotFoundException
     */
    public void deleteReview(Long reviewId) throws ReviewNotFoundException;

    /**
     *
     * @return
     */
    public List<Review> retrieveAllReview();
    
}
