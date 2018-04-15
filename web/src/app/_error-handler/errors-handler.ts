import {ErrorHandler, Injectable, Injector, NgZone} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {TokenService} from "../_services/token.service";
import {AlertService} from "../_services/alert.service";

import {NotificationsService} from 'angular2-notifications';


@Injectable()
export class ErrorsHandler implements ErrorHandler {

  constructor(private tokenService: TokenService,
              private notificationService: NotificationsService,
              private ngZone: NgZone) {
  }

  handleError(error: any) {
    if (error.error.error instanceof SyntaxError) {
      return this.showError("Error", "SOME TEXT HERE", 'alert');
    }
    switch (error.status) {
      // status code 0 means no response from the server
      case 0 : {
        this.showError("Error", "Connection timeout. Please try again later.", 'alert');
        break;
      }
      case 401 : {
        this.tokenService.removeToken();
        this.showError("401", "Some text here", "alert");
        break;
      }
      // catch all status codes
      default : {
        this.showError("Error", error.status + " " + error.message, "error")
      }
    }
    console.log(error)
  }


  showError(title: string, content: string, type: string) {
    //here are settings for notifications
    const options: any = {
      position :["top","left" ],
      type: 'warn',
      timeOut: 5000,
      showProgressBar: true,
      pauseOnHover: true,
      clickToClose: true,
      animate: 'fade'
    };
    this.ngZone.run(() => {
      this.notificationService.create(title, content, type, options)
    });
  }
}

