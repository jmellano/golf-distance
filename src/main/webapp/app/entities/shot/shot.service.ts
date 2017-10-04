import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Shot } from './shot.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ShotService {

    private resourceUrl = SERVER_API_URL + 'api/shots';

    constructor(private http: Http) { }

    create(shot: Shot): Observable<Shot> {
        const copy = this.convert(shot);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(shot: Shot): Observable<Shot> {
        const copy = this.convert(shot);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Shot> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(shot: Shot): Shot {
        const copy: Shot = Object.assign({}, shot);
        return copy;
    }
}
