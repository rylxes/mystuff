import {ErrorHandler, Inject, Injectable, Injector, NgZone} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {TokenService} from "../_services/token.service";
import {AlertService} from "../_services/alert.service";

import {NotificationsService} from 'angular2-notifications';
import {HANDLERS, HttpErrorHandler} from "./http.error.handler.interface";


@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

  constructor(private tokenService: TokenService,
              private notificationService: NotificationsService,
              private ngZone: NgZone,
              @Inject(HANDLERS) private handlers: HttpErrorHandler[]) {
  }

  handleError(error: any) {
    for (let errorHandler of this.handlers) {
      if (errorHandler.acceptable(error)) {
        errorHandler.handle(error);
      }
    }


  }


  showError(title: string, content: string, type: string) {
    //here are settings for notifications
    const options: any = {
      position: ["top", "left"],
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

