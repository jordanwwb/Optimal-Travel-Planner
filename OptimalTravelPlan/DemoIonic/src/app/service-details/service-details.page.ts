import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Booking } from '../models/booking';
import { Customer } from '../models/customer';
import { Service } from '../models/service';
import { TravelItinerary } from '../models/travel-itinerary';
import { ServiceService } from '../services/service.service';
import { ModalController } from '@ionic/angular';
import { CreateNewBookingPage } from '../create-new-booking/create-new-booking.page';

@Component({
  selector: 'app-service-details',
  templateUrl: './service-details.page.html',
  styleUrls: ['./service-details.page.scss'],
})
export class ServiceDetailsPage implements OnInit {

  service: Service;
  private customer: Customer;
  private password: string;
  retrieveServiceError: boolean;
  booking: Booking;

  constructor(private activatedRoute: ActivatedRoute,
    private serviceService: ServiceService,
    private router: Router,
    public modalController: ModalController) {
    this.retrieveServiceError = false;
    this.booking = new Booking();
  }

  ngOnInit() {
    let serID: number = parseInt(this.activatedRoute.snapshot.paramMap.get('serviceId'));
    console.log("init travel itin details page");
    let tempCus = sessionStorage['customer'];
    if (tempCus != null) {
      this.customer = JSON.parse(tempCus);
      this.password = sessionStorage['password'];
    }
    this.serviceService.retrieveAllActiveServices().subscribe({
      next: (response) => {
        let services: Service[] = response;
        for (let s of services) {
          if (s.serviceId == serID) {
            this.service = s;
          }
        }
      },
      error: (error) => {
        this.retrieveServiceError = true;
        console.log('********** retrieve service error: ' + error);
      }
    });
  }

  async addToTravelItinerary() {
    const modal = await this.modalController.create({
      component: CreateNewBookingPage,
      componentProps: { value: this.service.serviceName }
    });

    modal.onDidDismiss().then((event) => {
      let itin = sessionStorage['travelItinerary'];
      console.log(itin);
      if (itin != null) {
        console.log("Adding to existing travel itin!");
        let sessionItin: TravelItinerary;
        sessionItin = JSON.parse(itin);
        this.booking = new Booking(null, event.data.start, event.data.end,
          null, this.service);
          if(sessionItin.bookings == null){
            sessionItin.bookings = [];
          }
          if(sessionItin.country == null){
            sessionItin.country = this.service.country;
          }
        sessionItin.bookings.push(this.booking);
        console.log("Adding to sessionStorage");
        sessionStorage['travelItinerary'] = JSON.stringify(sessionItin);
      } else {
        console.log("Creating new travel itin!");
        let sessionItin = new TravelItinerary();
        sessionItin.customer = this.customer;
        sessionItin.country = this.service.country;
        sessionItin.startDate = this.booking.startDate;
        sessionItin.endDate = this.booking.endDate;
        this.booking = new Booking(null, event.data.start, event.data.end, null, this.service);
        sessionItin.bookings = [this.booking];
        console.log("Adding to sessionStorage");
        sessionStorage['travelItinerary'] = JSON.stringify(sessionItin);
      }
      
      console.log("Attempting to route to travelItineraryDetails");
      this.router.navigate(["/travelItineraryDetails"]);
    });

    await modal.present();


  }

}