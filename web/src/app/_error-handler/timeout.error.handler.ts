import {HttpErrorHandler} from "./http.error.handler.interface";
import {NotificationsService} from 'angular2-notifications';
import {Injectable, NgZone} from "@angular/core";

@Injectable()
export class TimeoutErrorHandler implements HttpErrorHandler {
  constructor(private notificationService: NotificationsService,
              private ngZone: NgZone) {
  }

  acceptable(error: any): boolean {
    return (error.status == 0)
  }

  handle(error: any): void {
    this.ngZone.run(() => {
      this.notificationService.warn("Error", "No connection. Please try later.");
    });
  }
}
