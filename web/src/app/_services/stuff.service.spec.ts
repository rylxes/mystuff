import {StuffService} from "./stuff.service";
import {TestBed} from "@angular/core/testing";

TestBed.configureTestingModule({
  providers: [
    StuffService
  ]
});
describe('StuffServiceTest', () => {
  //
  // describe('getStuff()',
  //   () => {
  //     it('should call back service with correct params',
  //       inject([StuffService], (stuffService) => {
  //         stuffService.getStuff(1)
  //       }));
  //   });
  it('true is true', () => expect(true).toBe(true));
});
