/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GolfDistanceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TypeReferentielDetailComponent } from '../../../../../../main/webapp/app/entities/type-referentiel/type-referentiel-detail.component';
import { TypeReferentielService } from '../../../../../../main/webapp/app/entities/type-referentiel/type-referentiel.service';
import { TypeReferentiel } from '../../../../../../main/webapp/app/entities/type-referentiel/type-referentiel.model';

describe('Component Tests', () => {

    describe('TypeReferentiel Management Detail Component', () => {
        let comp: TypeReferentielDetailComponent;
        let fixture: ComponentFixture<TypeReferentielDetailComponent>;
        let service: TypeReferentielService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GolfDistanceTestModule],
                declarations: [TypeReferentielDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TypeReferentielService,
                    JhiEventManager
                ]
            }).overrideTemplate(TypeReferentielDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeReferentielDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeReferentielService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TypeReferentiel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.typeReferentiel).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
