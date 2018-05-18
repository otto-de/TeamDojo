import { IOrganization } from 'app/shared/model/organization.model';

export class ProfileInfo {
    activeProfiles: string[];
    ribbonEnv: string;
    inProduction: boolean;
    swaggerEnabled: boolean;
    organization: IOrganization;
}
