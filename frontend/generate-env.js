import { writeFileSync } from 'fs';
import { config } from 'dotenv';

// Carica le variabili dal file .env (se presente)
const parsed = config().parsed || {};

// Usa le variabili d'ambiente di runtime come fallback (process.env)
const env = {
  ...process.env,
  ...parsed
};

// Genera il contenuto del file environment.ts
const environmentFileContent = `
export const environment = {
  production: false,
  googleApiKey: '${env.GOOGLE_API_KEY || ''}',
  geoapifyToken: '${env.GEOAPIFY_TOKEN || ''}',
  apiBaseUrl: '${env.API_BASE_URL || ''}'
};
`;
// Scrive il file environment.ts
writeFileSync('./src/environments/environment.ts', environmentFileContent);

// Genera il contenuto del file environment.prod.ts
const environmentProdFileContent = `
export const environment = {
  production: true,
  googleApiKey: '${env.GOOGLE_API_KEY || ''}',
  geoapifyToken: '${env.GEOAPIFY_TOKEN || ''}',
  apiBaseUrl: '${env.API_BASE_URL || ''}'
};
`;

// Scrive il file environment.prod.ts
writeFileSync('./src/environments/environment.prod.ts', environmentProdFileContent);

console.log('Environment files generated successfully.');