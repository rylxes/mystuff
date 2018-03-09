import {Component, OnInit} from "@angular/core";
import {Stuff} from "../_models/Stuff";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {StuffService} from "../_services/stuff.service";
import {ENTER, COMMA} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from "@angular/material";

@Component({
  selector: 'add-stuff',
  templateUrl: './add-stuff.component.html',
  styleUrls: ['./add-stuff.component.css']
})
export class AddStuffComponent implements OnInit {

  addStuff: FormGroup;
  model: Stuff = new Stuff(0, "", "", []);
  visible: boolean = true;
  selectable: boolean = true;
  removable: boolean = true;
  addOnBlur: boolean = true;
  separatorKeysCodes = [ENTER, COMMA];
  categories = [];


  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private stuffService: StuffService) {
    this.stuffFormInit();
  }

  stuffFormInit() {
    this.addStuff = this.fb.group({
      name: '',
      description: '',
      categories: this.fb.array([])
    });
  }

  createCategory(category: any) {
    return this.fb.group({
      name: category
    });
  }

  addNewCategory(category: any) {
    const control = <FormArray>this.addStuff.controls['categories'];
    control.push(this.createCategory(category));
  }

  deleteCategory(index: number) {
    const control = <FormArray>this.addStuff.controls['categories'];
    control.removeAt(index);
  }

  add(event: MatChipInputEvent): void {
    let input = event.input;
    let value = event.value;


    // Add our category
    if ((value || '').trim()) {
      this.categories.push({name: value.trim()});
      this.addNewCategory(value.trim())
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(category: any): void {
    let index = this.categories.indexOf(category);

    if (index >= 0) {
      this.categories.splice(index, 1);
      this.deleteCategory(index)
    }
  }


  save() {
    // let headers = new Headers();
    // headers.append("Content-Type", 'application/json');
    // headers.append("X-Auth-Token", "Bearer " + localStorage.getItem("token"));
    // let requestOptions = new RequestOptions({headers: headers});
    // this.userService.create(this.addStuff.value).subscribe(stuff => {
    //   console.log(stuff);
    //
    // });
    // this.http.post(this.restService.getUrl() + "/rest/stuff", this.addStuff.value, this.restService.defaultHeaders()).subscribe(stuff => {
    this.stuffService.addStuff(this.addStuff.value).subscribe(stuff => {
      //TODO: update data
      console.log(stuff);
    });
  }

  ngOnInit() {
  }


}
