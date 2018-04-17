import {BrowserModule} from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {AddStuffComponent} from './add-stuff/add-stuff.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './login';
import {RegisterComponent} from './register';
import {routing} from './app.routing';
import {HomeComponent} from './home';
import {AlertService, AuthenticationService, UserService} from "./_services";
import {AuthGuard} from "./_guards";
import {AlertComponent} from "./_directives/alert.component";
import {StuffListComponent} from './stuff-list/stuff-list.component';
import {StuffService} from "./_services/stuff.service";
import {MenuComponent} from './menu/menu.component';
import {StuffDetailsComponent} from './stuff-details/stuff-details.component';
import {TokenService} from "./_services/token.service";
import {ConfigService} from "./_services/config.service";
import {AuthHttpIntercept} from "./_intercept/auth-http-intercept";
import {MatChipsModule, MatFormFieldModule, MatIconModule} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {GlobalErrorHandler} from "./_error-handler/global.error.handler";
import {Subject} from "rxjs/Subject";
import {SimpleNotificationsModule} from 'angular2-notifications';
import {HANDLERS} from "./_error-handler/http.error.handler.interface";
import {SyntaxErrorHandler} from "./_error-handler/syntax.error.handler";
import {TimeoutErrorHandler} from "./_error-handler/timeout.error.handler";
import {UnauthorizedErrorHandler} from "./_error-handler/unuathorized.error.handler";
import {DefaultHttpCodesErrorHandler} from "./_error-handler/default.httpcode.handler";


@NgModule({
  declarations: [
    AppComponent,
    AddStuffComponent,
    LoginComponent,
    AlertComponent,
    RegisterComponent,
    HomeComponent,
    StuffListComponent,
    MenuComponent,
    StuffDetailsComponent,

  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    routing,
    BrowserModule,
    ReactiveFormsModule,
    MatChipsModule,
    MatIconModule,
    MatFormFieldModule,
    BrowserAnimationsModule,
    SimpleNotificationsModule.forRoot()
  ],

  providers: [
    Subject,
    AuthGuard,
    AlertService,
    AuthenticationService,
    ConfigService,
    TokenService,
    StuffService,
    UserService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHttpIntercept,
      multi: true
    },
    {
      provide: ErrorHandler,
      useClass: GlobalErrorHandler,
    }
    , {
      provide: HANDLERS,
      useClass: SyntaxErrorHandler,
      multi: true
    },
    {
      provide: HANDLERS,
      useClass: TimeoutErrorHandler,
      multi: true
    },
    {
      provide: HANDLERS,
      useClass: UnauthorizedErrorHandler,
      multi: true
    },
    {
      provide: HANDLERS,
      useClass: DefaultHttpCodesErrorHandler,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
