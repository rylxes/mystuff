import {HttpErrorHandler} from "./http.error.handler.interface";
import {NotificationsService} from 'angular2-notifications';
import {Injectable, NgZone} from "@angular/core";


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
