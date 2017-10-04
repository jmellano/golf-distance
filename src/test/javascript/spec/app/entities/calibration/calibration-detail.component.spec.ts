/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GolfDistanceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CalibrationDetailComponent } from '../../../../../../main/webapp/app/entities/calibration/calibration-detail.component';
import { CalibrationService } from '../../../../../../main/webapp/app/entities/calibration/calibration.service';
import { Calibration } from '../../../../../../main/webapp/app/entities/calibration/calibration.model';

describe('Component Tests', () => {

    describe('Calibration Management Detail Component', () => {
        let comp: CalibrationDetailComponent;
        let fixture: ComponentFixture<CalibrationDetailComponent>;
        let service: CalibrationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GolfDistanceTestModule],
                declarations: [CalibrationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CalibrationService,
                    JhiEventManager
                ]
            }).overrideTemplate(CalibrationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CalibrationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalibrationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Calibration(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.calibration).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
