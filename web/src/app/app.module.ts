import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AddStuffComponent} from './add-stuff/add-stuff.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {routing} from './app.routing';
import {HomeComponent} from './home/home.component';
import {UserService} from "./_services/user.service";
import {AuthenticationService} from "./_services/authentication.service";
import {AlertService} from "./_services/alert.service";
import {AuthGuard} from "./_guards/auth.guard";
import {AlertComponent} from "./_directives/alert.component";
import {HttpModule} from "@angular/http";
import {StuffListComponent} from './stuff-list/stuff-list.component';
import {StuffService} from "./_services/stuff.service";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MenuComponent} from './menu/menu.component';

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
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    ReactiveFormsModule,
    routing,
    NgbModule.forRoot()
  ],
  providers: [
    AuthGuard,
    AlertService,
    AuthenticationService,
    StuffService,
    UserService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
