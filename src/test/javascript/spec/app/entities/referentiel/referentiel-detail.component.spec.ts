/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GolfDistanceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReferentielDetailComponent } from '../../../../../../main/webapp/app/entities/referentiel/referentiel-detail.component';
import { ReferentielService } from '../../../../../../main/webapp/app/entities/referentiel/referentiel.service';
import { Referentiel } from '../../../../../../main/webapp/app/entities/referentiel/referentiel.model';

describe('Component Tests', () => {

    describe('Referentiel Management Detail Component', () => {
        let comp: ReferentielDetailComponent;
        let fixture: ComponentFixture<ReferentielDetailComponent>;
        let service: ReferentielService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GolfDistanceTestModule],
                declarations: [ReferentielDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReferentielService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReferentielDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReferentielDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReferentielService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Referentiel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.referentiel).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
