

export const environment = {
  production: false,
  googleApiKey: process.env["GOOGLE_API_KEY"] || 'default-google-api-key',
  geoapifyToken: process.env["GEOAPIFY_TOKEN"] || 'default-geoapify-token'
};