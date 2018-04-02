import {TestBed} from "@angular/core/testing";

import {ConfigService} from "./config.service";



describe('ConfigService', () => {

  let configService: ConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ConfigService],
    });
    configService = TestBed.get(ConfigService);
  });

  it('#getBackUrl should return expected value', () => {
    const url = "http://localhost:9000";
    expect(configService.getBackUrl()).toEqual(url);

  });
});
