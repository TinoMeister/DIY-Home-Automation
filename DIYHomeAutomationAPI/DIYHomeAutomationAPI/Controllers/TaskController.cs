using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TaskController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public TaskController(SensorDbContext context) => _context = context;

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Models.Task>>> GetRooms() =>
            await _context.Tasks.ToListAsync();
    }
}
