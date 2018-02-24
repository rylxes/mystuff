import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../_services";

@Component({
  selector: 'main-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private authService: AuthenticationService) {
  }

  ngOnInit() {
  }

  isAuthorized(): boolean {
    return this.authService.isAuthorized()
  }

  logout() {
    this.authService.logout();
    location.reload();
  }
}
