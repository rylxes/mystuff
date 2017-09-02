import {Component, OnInit} from "@angular/core";
import {Stuff} from "../model/Stuff";
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
    this.stuffFormInit();
  }

  stuffFormInit() {
    this.addStuff = this.fb.group({
      name: '',
      description: '',
      categories: this.fb.array([])
    });
  }

  save() {
    console.log(this.addStuff.value);
  }

  ngOnInit() {
  }

}
