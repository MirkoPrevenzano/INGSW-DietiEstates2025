/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [ "./src/**/*.{html,ts,scss}"],
  theme: {
    extend: {
      colors: {
        primary: '#2C3E50',
        secondary: '#27AE60',
        tertiary: '#F4F4F4',
        success: '#28a745',
        error: '#dc3545',
        warning: '#ffc107',
        grayLight: '#F5F5F5',
        grayDark: '#A9A9A9',
        back: '#F4F4F4',
        text: '#333333'
      }
    },
  },
  plugins: [],
}

