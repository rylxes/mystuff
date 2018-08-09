import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, ErrorHandler, NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {AddStuffComponent} from './add-stuff/add-stuff.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './login';
import {RegisterComponent} from './register';
import {routing} from './app.routing';
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
import {MatAutocompleteModule, MatChipsModule, MatFormFieldModule, MatIconModule} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ErrorsHandler} from "./_error-handler/errors-handler";
import {Subject} from "rxjs/Subject";
import {AddCategoryComponent} from './add-category/add-category.component';

const appInitializer = (appConfig: ConfigService) => {
  return () => {
    return appConfig.init();
  };
};

@NgModule({
  declarations: [
    AppComponent,
    AddStuffComponent,
    LoginComponent,
    AlertComponent,
    RegisterComponent,
    StuffListComponent,
    MenuComponent,
    StuffDetailsComponent,
    AddCategoryComponent,

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
    MatAutocompleteModule,
    BrowserAnimationsModule


  ],

  providers: [
    Subject,
    AuthGuard,
    AlertService,
    AuthenticationService,
    ConfigService,
    {
      provide: APP_INITIALIZER,
      useFactory: appInitializer,
      multi: true,
      deps: [ConfigService]
    },
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
      useClass: ErrorsHandler,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
