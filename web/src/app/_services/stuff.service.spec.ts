import {TestBed} from "@angular/core/testing";

import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {ConfigService} from "./config.service";
import {StuffService} from "./stuff.service";
import {UserService} from "./user.service";
import {Stuff} from "../_models/Stuff";


describe('StuffService', () => {

  let stuffService: StuffService;
  let httpMock: HttpTestingController;
  let configService: ConfigService;
  const testStuff: Stuff = {id: 1, name: 'testUser', description: 'TestDescription', categories: []};
  const testStuffArray: Stuff[] = [
    {id: 1, name: 'testUser1', description: 'TestDescription1', categories: []},
    {id: 2, name: 'testUser2', description: 'TestDescription2', categories: []}
  ];
  const testId: number = 1;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StuffService, ConfigService, UserService],
      imports: [HttpClientTestingModule]
    });
    httpMock = TestBed.get(HttpTestingController);
    stuffService = TestBed.get(StuffService);
    configService = TestBed.get(ConfigService);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('#getStuff should return expected Stuff', () => {
    stuffService.getStuff(testId).subscribe(stuff => {
        expect(stuff).toEqual(jasmine.objectContaining(testStuff));
      }
    );
    const req = httpMock.expectOne(configService.getBackUrl() + '/rest/stuff/' + testId);
    expect(req.request.method).toEqual('GET');
    req.flush(testStuff);
  });

  it('#getMyStuff should return expected list of Stuffs', () => {
    stuffService.getMyStuff().subscribe(stuff => {
        expect(stuff).toEqual(testStuffArray);
      }
    );
    const req = httpMock.expectOne(configService.getBackUrl() + '/rest/stuff/list');
    expect(req.request.method).toEqual('GET');
    req.flush(testStuffArray);
  });

  it('#addStuff should save and return correct stuff', () => {
    stuffService.addStuff(testStuff).subscribe(stuff => {
        expect(stuff).toEqual(jasmine.objectContaining(testStuff));
      }
    );
    const req = httpMock.expectOne(configService.getBackUrl() + '/rest/stuff');
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(testStuff);
    req.flush(testStuff);
  });

  it('#delete should works correct', () => {
    stuffService.delete(testId).subscribe(() => {
      }
    );
    const req = httpMock.expectOne(configService.getBackUrl() + '/rest/stuff/' + testId);
    expect(req.request.method).toEqual('DELETE');
  });
});
