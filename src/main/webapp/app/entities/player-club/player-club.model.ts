import { BaseEntity } from './../../shared';

export class PlayerClub implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public label?: string,
        public player?: BaseEntity,
        public shots?: BaseEntity[],
        public isCalibrateds?: BaseEntity[],
    ) {
    }
}
