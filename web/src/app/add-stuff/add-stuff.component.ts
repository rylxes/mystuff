import {Component, OnInit} from "@angular/core";
import {Stuff} from "../_models/Stuff";
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";

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
    // let headers = new Headers();
    // headers.append("Content-Type", 'application/json');
    // headers.append("X-Auth-Token", "Bearer " + localStorage.getItem("currentUser"));
    // let requestOptions = new RequestOptions({headers: headers});
    this.http.post("http://localhost:9000/rest/stuff", this.addStuff.value, {headers: new HttpHeaders("Authorization: Bearer " + localStorage.getItem("currentUser"))}).subscribe(stuff => {
      //TODO: update data
      console.log(stuff);
    });
  }

  ngOnInit() {
  }

}
