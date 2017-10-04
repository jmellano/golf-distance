/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GolfDistanceTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlayerClubDetailComponent } from '../../../../../../main/webapp/app/entities/player-club/player-club-detail.component';
import { PlayerClubService } from '../../../../../../main/webapp/app/entities/player-club/player-club.service';
import { PlayerClub } from '../../../../../../main/webapp/app/entities/player-club/player-club.model';

describe('Component Tests', () => {

    describe('PlayerClub Management Detail Component', () => {
        let comp: PlayerClubDetailComponent;
        let fixture: ComponentFixture<PlayerClubDetailComponent>;
        let service: PlayerClubService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GolfDistanceTestModule],
                declarations: [PlayerClubDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlayerClubService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlayerClubDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlayerClubDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlayerClubService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlayerClub(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.playerClub).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
