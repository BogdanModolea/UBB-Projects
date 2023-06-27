import React from 'react'
import { CssBaseline, Container, Typography } from '@mui/material'

function AppHome() {
    return (
        <React.Fragment>
            <CssBaseline />
                <Container maxWidth="xl">
                    <Typography variant="h1" component="h1" gutterBottom>
                        Welcome to the app! Use the menu to navigate.
                    </Typography>
                </Container>
        </React.Fragment>
    );
};

export default AppHome;
