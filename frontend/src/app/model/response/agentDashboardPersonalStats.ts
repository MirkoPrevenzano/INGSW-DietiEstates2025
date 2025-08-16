import { AgentStats } from "./support/agentStats";

export interface AgentDashboardPersonalStats{
    agentStatsDto:AgentStats,
    monthlyDeals:number[]
}