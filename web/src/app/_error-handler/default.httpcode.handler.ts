import {HttpErrorHandler} from "./http.error.handler.interface";
import {NotificationsService} from "angular2-notifications";
import {NgZone} from "@angular/core";

export class DefaultHttpCodesErrorHandler implements HttpErrorHandler {
  constructor(private notificationService: NotificationsService
    ,private ngZone: NgZone){}
  acceptable(error: any): boolean {
    return true;
  }

  handle(error: any): void {
    this.ngZone.run(() => {
      this.notificationService.bare("Error","some text here");
    });
    console.log(error);
  }

}
