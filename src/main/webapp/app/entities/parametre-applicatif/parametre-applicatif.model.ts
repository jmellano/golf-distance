import { BaseEntity } from './../../shared';

export class ParametreApplicatif implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string,
        public valeur?: string,
    ) {
    }
}
