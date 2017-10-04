import { BaseEntity } from './../../shared';

export class Shot implements BaseEntity {
    constructor(
        public id?: number,
        public force?: number,
        public distance?: number,
        public playerClub?: BaseEntity,
    ) {
    }
}
