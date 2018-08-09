import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {StuffDetailsComponent} from './stuff-details.component';

xdescribe('StuffDetailsComponent', () => {
  let component: StuffDetailsComponent;
  let fixture: ComponentFixture<StuffDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [StuffDetailsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StuffDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
