using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Reflection;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public UserController(SensorDbContext context) => _context = context;

        /// <summary>
        /// This method search in the database all the Users.
        /// </summary>
        /// <returns>List with all the Users</returns>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<User>>> GetUsers() =>
            await _context.Users.ToListAsync();

        /// <summary>
        /// This method creates a new User
        /// </summary>
        /// <param name="user">User's Object</param>
        /// <returns>Method result</returns>
        [HttpPost]
        public async Task<ActionResult<User>> PostUser(User? user)
        {
            // Verify if the user receibed is null
            if (user is null)
                BadRequest();
            // If the userName alredy exists then throw a exception
            if (await _context.Users.Where(u => u.Name.Equals(user.Name)).FirstOrDefaultAsync() is not null)
                BadRequest();

            // Add the User to an entity entry to insert into the database
            _context.Users.Add(user);

            // Try to save the entry to the database
            await _context.SaveChangesAsync();

            // If is successfully then returns the last User Inserted
            return await _context.Users.OrderByDescending(u => u.Id).FirstAsync();
        }

        /// <summary>
        /// This method updates an User
        /// </summary>
        /// <param name="id">User's id</param>
        /// <param name="user">User object</param>
        /// <returns>Method result</returns>
        [HttpPut("{id}")]
        public async Task<ActionResult> PutUser(int id, User? user)
        {
            // Verify if the user receibed is not null or if the user id are the same
            if (user is null || id != user.Id)
                BadRequest();

            // If the Name alredy exists then throw a BadRequest()
            if (await _context.Users.Where(u => !u.Id.Equals(user.Id) && u.Name.Equals(user.Name)).FirstOrDefaultAsync() is not null)
                BadRequest();

            // Put the user as an entry an set the sate as modified to update the database
            _context.Entry(user).State = EntityState.Modified;

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }

        /// <summary>
        /// This method removes a User from the database
        /// </summary>
        /// <param name="id">User's id</param>
        /// <returns>Method result</returns>
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteUser(int id)
        {
            // Verify if the are any users in the database, if not then throw exception
            if (_context.Users.IsNullOrEmpty())
                BadRequest();

            // Get the user by the id
            User? user = await _context.Users.FindAsync(id);

            // Send the user to the method DeleteUser 
            return await DeleteUser(user);
        }

        /// <summary>
        ///  This method removes a User from the database
        /// </summary>
        /// <param name="user">User's object</param>
        /// <returns>Method result</returns>
        [HttpDelete]
        public async Task<ActionResult> DeleteUser(User? user)
        {
            // Verify if the are any users in the database or if the user is null, if not then throw exception
            if (_context.Users.IsNullOrEmpty() || user is null)
                BadRequest();
            // Put the user as an entry an set the sate as remove from database
            _context.Users.Remove(user);

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }
    }
}