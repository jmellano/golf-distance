/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GolfDistanceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ParametreApplicatifDetailComponent } from '../../../../../../main/webapp/app/entities/parametre-applicatif/parametre-applicatif-detail.component';
import { ParametreApplicatifService } from '../../../../../../main/webapp/app/entities/parametre-applicatif/parametre-applicatif.service';
import { ParametreApplicatif } from '../../../../../../main/webapp/app/entities/parametre-applicatif/parametre-applicatif.model';

describe('Component Tests', () => {

    describe('ParametreApplicatif Management Detail Component', () => {
        let comp: ParametreApplicatifDetailComponent;
        let fixture: ComponentFixture<ParametreApplicatifDetailComponent>;
        let service: ParametreApplicatifService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GolfDistanceTestModule],
                declarations: [ParametreApplicatifDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ParametreApplicatifService,
                    JhiEventManager
                ]
            }).overrideTemplate(ParametreApplicatifDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParametreApplicatifDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametreApplicatifService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ParametreApplicatif(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.parametreApplicatif).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
