import {Component, OnInit} from '@angular/core';
import {StuffService} from "../_services/stuff.service";

@Component({
  selector: 'stuff-list',
  templateUrl: './stuff-list.component.html',
  styleUrls: ['./stuff-list.component.css']
})
export class StuffListComponent implements OnInit {

  private stuffList;

  constructor(private stuffService: StuffService) {
  }

  ngOnInit() {
    this.stuffService.getMyStuff().subscribe(stuffList => {
      console.log(stuffList.json());
      this.stuffList = stuffList.json();
    });
  }
}
