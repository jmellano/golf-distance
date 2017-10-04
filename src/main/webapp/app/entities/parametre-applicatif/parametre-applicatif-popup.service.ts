import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ParametreApplicatif } from './parametre-applicatif.model';
import { ParametreApplicatifService } from './parametre-applicatif.service';

@Injectable()
export class ParametreApplicatifPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private parametreApplicatifService: ParametreApplicatifService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.parametreApplicatifService.find(id).subscribe((parametreApplicatif) => {
                    this.ngbModalRef = this.parametreApplicatifModalRef(component, parametreApplicatif);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.parametreApplicatifModalRef(component, new ParametreApplicatif());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    parametreApplicatifModalRef(component: Component, parametreApplicatif: ParametreApplicatif): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.parametreApplicatif = parametreApplicatif;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
