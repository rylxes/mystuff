import {HttpErrorHandler} from "./http.error.handler.interface";
import {NotificationsService} from 'angular2-notifications';
import {Injectable, NgZone} from "@angular/core";
import {getStatusText, UNAUTHORIZED} from "http-status-codes";

@Injectable()
export class UnauthorizedErrorHandler implements HttpErrorHandler {
  constructor(private notificationService: NotificationsService,
              private ngZone: NgZone) {
  }

  acceptable(error: any): boolean {
    return (error.status == UNAUTHORIZED)
  }

  handle(error: any): void {
    this.ngZone.run(() => {
      this.notificationService.error("Error 401", getStatusText(error.status));
    });
  }
}
