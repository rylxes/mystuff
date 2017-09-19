import {Component, OnInit} from "@angular/core";
import {Stuff} from "../model/Stuff";
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'add-stuff',
  templateUrl: './add-stuff.component.html',
  styleUrls: ['./add-stuff.component.css']
})
export class AddStuffComponent implements OnInit {

  addStuff: FormGroup;
  model: Stuff = new Stuff(0, "", "", []);

  constructor(private fb: FormBuilder,
              private http: HttpClient) {
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
    this.http.post("http://localhost:9000/rest/stuff", this.addStuff.value).subscribe(stuff => {
      //TODO: update data
      console.log(stuff);
    });
  }

  ngOnInit() {
  }

}
