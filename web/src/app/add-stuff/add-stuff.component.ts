import { Component, OnInit } from '@angular/core';
import {Stuff} from "../model/Stuff";
import {Category} from "../model/Category";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-add-stuff',
  templateUrl: './add-stuff.component.html',
  styleUrls: ['./add-stuff.component.css']
})
export class AddStuffComponent implements OnInit {

  addStuff: FormGroup;
  model: Stuff = new Stuff(0, "", "", []);

  constructor(private fb: FormBuilder) {

  }

  ngOnInit() {
  }

}
