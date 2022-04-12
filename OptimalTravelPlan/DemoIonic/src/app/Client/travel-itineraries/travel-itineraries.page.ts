
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertController } from '@ionic/angular';
import { Customer } from 'src/app/models/customer';
import { TravelItinerary } from 'src/app/models/travel-itinerary';
import { TravelItineraryService } from 'src/app/services/travel-itinerary.service';

@Component({
  selector: 'app-travel-itineraries',
  templateUrl: './travel-itineraries.page.html',
  styleUrls: ['./travel-itineraries.page.scss'],
})
export class TravelItinerariesPage implements OnInit {

  customer: Customer;
  password: string;
  itineraries: TravelItinerary[];

  constructor(private router: Router,
    private travelItineraryService: TravelItineraryService,
    public alertController: AlertController) { }

  ngOnInit() {
    this.customer = JSON.parse(sessionStorage['customer']);
    this.password = sessionStorage['password'];
    this.travelItineraryService.retrieveAllTravelItinerary(this.customer.username, this.password).subscribe({
      next: (response) => {
        this.itineraries = (response);
      },
      error: (error) => {
        console.log('********** get customer itineraries error: ' + error);
      }
    });
  }

  viewItiDetails(event, iti: TravelItinerary) {
    console.log("attempting to view travelItinerary = " + iti.travelItineraryId);
    //this.router.navigate(["/TravelItinerary/" + iti.travelItineraryId]);
  }
}


