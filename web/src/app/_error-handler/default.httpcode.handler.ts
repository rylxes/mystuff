import {HttpErrorHandler} from "./http.error.handler.interface";
import {NotificationsService} from 'angular2-notifications';
import {Injectable, NgZone} from "@angular/core";
import {getStatusText} from "http-status-codes";


@Injectable()
export class DefaultHttpCodesErrorHandler implements HttpErrorHandler {
  constructor(private notificationService: NotificationsService,
              private ngZone: NgZone) {
  }

  acceptable(error: any): boolean {
    return true;
  }

  handle(error: any): void {
    this.ngZone.run(() => {
      this.notificationService.warn("Error " + error.status , getStatusText(error.status));
      });
    console.log(error);
  }
}
