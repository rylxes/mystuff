import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../_services/authentication.service";

@Component({
  selector: 'menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private authService: AuthenticationService) {
  }

  ngOnInit() {
  }

  logout() {
    this.authService.logout();
    location.reload();
  }
}
