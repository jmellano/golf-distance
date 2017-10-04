/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GolfDistanceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShotDetailComponent } from '../../../../../../main/webapp/app/entities/shot/shot-detail.component';
import { ShotService } from '../../../../../../main/webapp/app/entities/shot/shot.service';
import { Shot } from '../../../../../../main/webapp/app/entities/shot/shot.model';

describe('Component Tests', () => {

    describe('Shot Management Detail Component', () => {
        let comp: ShotDetailComponent;
        let fixture: ComponentFixture<ShotDetailComponent>;
        let service: ShotService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GolfDistanceTestModule],
                declarations: [ShotDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShotService,
                    JhiEventManager
                ]
            }).overrideTemplate(ShotDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShotDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShotService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Shot(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shot).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
