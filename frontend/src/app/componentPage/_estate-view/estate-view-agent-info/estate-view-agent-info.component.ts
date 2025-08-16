import { Component, Input } from '@angular/core';
import { AgentPublicInfo } from '../../../model/response/support/agentPublicInfo';

@Component({
  selector: 'app-estate-view-agent-info',
  imports: [],
  templateUrl: './estate-view-agent-info.component.html',
  styleUrl: './estate-view-agent-info.component.scss'
})
export class EstateViewAgentInfoComponent {
  @Input() agentInfo!:AgentPublicInfo

}
