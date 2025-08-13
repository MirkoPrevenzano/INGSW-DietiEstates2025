export interface AgentGeneralStats {
    totalUploadedRealEstates: number,
    totalSoldRealEstates: number,
    totalRentedRealEstates: number,
    salesIncome: number,
    rentalsIncome: number
}

//successRate= (totalSoldRealEstate+totaleRentedRealEstate)/totalUplodadedRealEstate (se !=0) *100