import {TestBed} from "@angular/core/testing";

import {ConfigService} from "./config.service";
import { CookieService } from 'ngx-cookie-service';

describe('ConfigService', () => {

  let configService: ConfigService;
  let cookieService: CookieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ConfigService, CookieService],
    });
    configService = TestBed.get(ConfigService);
    cookieService = TestBed.get(CookieService);
  });

  it('#getBackUrl should return expected value', () => {
    const url = "http://localhost:9000";
    expect(configService.getBackUrl()).toEqual(url);
  });

  it('#getBackUrl should return value from cookie', () => {
    let urlFromCookie = "someUrlFromCookie";
    spyOn(cookieService, 'get').and.returnValue(urlFromCookie);
    spyOn(cookieService, 'check').and.returnValue(true);
    expect(configService.getBackUrl()).toEqual(urlFromCookie);
  });
});
