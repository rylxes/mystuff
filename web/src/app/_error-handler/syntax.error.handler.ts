import {HttpErrorHandler} from "./http.error.handler.interface";
import {AlertService} from "../_services/alert.service";
import {Injectable, NgZone} from "@angular/core";
import {NotificationsService} from "angular2-notifications";

@Injectable()
export class SyntaxErrorHandler implements HttpErrorHandler {
  constructor(private notificationService: NotificationsService,
              private ngZone: NgZone) {
  }

  acceptable(error: any) {
    if (error.error.error instanceof SyntaxError) {
      return true;
    } else {
      return false;
    }
  }

  handle(error: any) {
    this.ngZone.run(() => {
      this.notificationService.bare("Error", "some text here");
    });
    console.log(error);
  }
}
