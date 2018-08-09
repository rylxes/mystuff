import {HttpErrorHandler} from "./http.error.handler.interface";
import {NotificationsService} from 'angular2-notifications';
import {Injectable, NgZone} from "@angular/core";


@Injectable()
export class SyntaxErrorHandler implements HttpErrorHandler {
  constructor(private notificationService: NotificationsService,
              private ngZone: NgZone) {
  }

  acceptable(error: any): boolean {
    return (error.error.error instanceof SyntaxError)
  }

  handle(error: any): void {
    this.ngZone.run(() => {
      this.notificationService.error("Error", "Something goes wrong");
    });
  }
}
