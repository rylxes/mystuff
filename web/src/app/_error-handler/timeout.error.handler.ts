import {HttpErrorHandler} from "./http.error.handler.interface";
import {NotificationsService} from "angular2-notifications";
import {NgZone} from "@angular/core";

export class TimeoutErrorHandler implements HttpErrorHandler {
  constructor(private notificationService: NotificationsService
    ,private ngZone: NgZone){}

  acceptable(error: any): boolean {
    if (error.status == 0) {
      return true;
    } else {
      return false;
    }
  }
  handle(error: any): void {
    this.ngZone.run(() => {
      this.notificationService.bare("Error","some text here");
    });
    console.log(error);
  }


}
