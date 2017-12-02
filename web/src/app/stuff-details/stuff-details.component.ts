import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {StuffService} from "../_services/stuff.service";

@Component({
  selector: 'app-stuff-details',
  templateUrl: './stuff-details.component.html',
  styleUrls: ['./stuff-details.component.css']
})
export class StuffDetailsComponent implements OnInit {
  stuff;

  constructor(private stuffService: StuffService,
              private  router: Router,
              private  route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.stuffService.getStuff(params['id']).subscribe(stuff => this.stuff = stuff.json());
    });
  }
}
