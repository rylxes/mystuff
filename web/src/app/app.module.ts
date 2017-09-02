import {ApolloClient} from 'apollo-client';

import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AddStuffComponent} from './add-stuff/add-stuff.component';
import {AlertModule} from 'ngx-bootstrap';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

// Create the client as outlined above
const client = new ApolloClient();

export function provideClient(): ApolloClient {
  return client;
}

@NgModule({
  declarations: [
    AppComponent,
    AddStuffComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AlertModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
