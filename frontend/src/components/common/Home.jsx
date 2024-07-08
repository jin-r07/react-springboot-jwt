import React from 'react';
import { Button, Box, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

export default function Home() {
  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      height="80vh"
      bgcolor="background.paper"
      padding={4}
      borderRadius={2}
      boxShadow={3}
    >
      <Typography variant="h3" component="h1" gutterBottom>
        Hello & Welcome,
      </Typography>
      <Box display="flex" gap={2} mt={2}>
        <Button variant="contained" color="primary" component={Link} to="/login">
          Login
        </Button>
        <Button variant="contained" color="secondary" component={Link} to="/register">
          Register
        </Button>
      </Box>
    </Box>
  );
}
