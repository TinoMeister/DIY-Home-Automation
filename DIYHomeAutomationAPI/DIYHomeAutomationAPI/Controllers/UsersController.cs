using DIYHomeAutomationAPI.Models;
using DIYHomeAutomationAPI.Services;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        // variables
        private readonly UserManager<ApplicationUser> _userManager;
        private readonly JwtService _jwtService;
        private readonly SensorDbContext _context;

        public UsersController(UserManager<ApplicationUser> userManager, JwtService jwtService, SensorDbContext context)
        {
            _userManager = userManager;
            _jwtService = jwtService;
            _context = context;
        }

        /// <summary>
        /// This method creates an Token for the user
        /// </summary>
        /// <param name="request">Authentication Request</param>
        /// <returns>Token</returns>
        [HttpPost("Login")]
        public async Task<ActionResult<AuthResponse>> Authenticate(AuthRequest request)
        {
            // Verify states
            if (!ModelState.IsValid) return BadRequest(ModelState);

            // Get user by email
            var managedUser = await _userManager.FindByEmailAsync(request.Email);

            // Verify if is null
            if (managedUser is null) return BadRequest("Bad credentials");

            // Verify password if correct
            var isPasswordValid = await _userManager.CheckPasswordAsync(managedUser, request.Password);

            // If not then return BadRequest
            if (!isPasswordValid) return BadRequest("Bad credentials");

            // Generate an Token
            var token = _jwtService.CreateToken(managedUser);

            // Return Token
            return Ok(token);
        }

        /// <summary>
        /// This method creates an new User and return an Token
        /// </summary>
        /// <param name="user">Registration Request</param>
        /// <returns>Token</returns>
        [HttpPost("Register")]
        public async Task<ActionResult<AuthResponse>> PostUser(RegistrationRequest user)
        {
            // Verify states
            if (!ModelState.IsValid) return BadRequest(ModelState);

            // If the Email alredy exists return BadRequest
            if (await _userManager.Users.AnyAsync(u => u.Email!.Equals(user.Email))) return BadRequest();

            // Create a new user with the username and email
            IdentityResult result = await _userManager.CreateAsync(
                new ApplicationUser { Name = user.Name, UserName = user.UserName, Email = user.Email },
                user.Password
            );

            // If something went wrong return BadRequest
            if (!result.Succeeded) return BadRequest(result.Errors);

            // Get user with all the data necessary
            ApplicationUser? tempUser = await _userManager.FindByEmailAsync(user.Email);

            // Verify if the user is null
            if (tempUser is null) return NotFound();

            // Generate Token
            AuthResponse token = _jwtService.CreateToken(tempUser);

            // Return Token
            return Ok(token);
        }
    }
}