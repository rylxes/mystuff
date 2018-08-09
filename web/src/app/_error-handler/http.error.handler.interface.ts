import {InjectionToken} from "@angular/core";
export const HANDLERS = new InjectionToken<HttpErrorHandler>('http-error-handler');

export interface HttpErrorHandler {
  acceptable(error: any): boolean;
  handle(error: any): void;
}
