/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GolfDistanceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ParametreMetierDetailComponent } from '../../../../../../main/webapp/app/entities/parametre-metier/parametre-metier-detail.component';
import { ParametreMetierService } from '../../../../../../main/webapp/app/entities/parametre-metier/parametre-metier.service';
import { ParametreMetier } from '../../../../../../main/webapp/app/entities/parametre-metier/parametre-metier.model';

describe('Component Tests', () => {

    describe('ParametreMetier Management Detail Component', () => {
        let comp: ParametreMetierDetailComponent;
        let fixture: ComponentFixture<ParametreMetierDetailComponent>;
        let service: ParametreMetierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GolfDistanceTestModule],
                declarations: [ParametreMetierDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ParametreMetierService,
                    JhiEventManager
                ]
            }).overrideTemplate(ParametreMetierDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParametreMetierDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametreMetierService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ParametreMetier(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.parametreMetier).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
